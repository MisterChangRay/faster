package com.github.misterchangray.monitor;


import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by ray.chang on 2022/1/19
 */
public class ProfilingMethodVisitor extends AdviceAdapter {

    private static final String PROFILING_ASPECT_INNER_NAME = Type.getInternalName(ProfilingAspect.class);
    private static final String PROFILING_ASPECT_EXCEPTION_INNER_NAME = Type.getInternalName(ProfilingExceptionAspect.class);

    private static final MethodTagMaintainer methodTagMaintainer = MethodTagMaintainer.getInstance();

    private final String innerClassName;

    private final String methodName;

    private final int methodTagId;

    private int startTimeIdentifier;

    private  Label from = new Label(),
            to = new Label(),
            target = new Label();

    public ProfilingMethodVisitor(int access,
                                  String name,
                                  String desc,
                                  MethodVisitor mv,
                                  String innerClassName,
                                  String fullClassName,
                                  String simpleClassName,
                                  String humanMethodDesc) {
        super(ASM9, mv, access, name, desc);
        this.methodName = name;
        this.methodTagId = methodTagMaintainer.addMethodTag(
                getMethodTag(fullClassName, simpleClassName, name, humanMethodDesc));
        this.innerClassName = innerClassName;
    }

    private MethodTag getMethodTag(String fullClassName,
                                   String simpleClassName,
                                   String methodName,
                                   String humanMethodDesc) {
        return MethodTag.getGeneralInstance(fullClassName, simpleClassName,  methodName, humanMethodDesc);
    }

    @Override
    protected void onMethodEnter() {
        if (profiling()) {
            visitLabel(from);
            visitTryCatchBlock(from, to, target, "java/lang/Exception");

            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
            startTimeIdentifier = newLocal(Type.LONG_TYPE);
            mv.visitVarInsn(LSTORE, startTimeIdentifier);
        }
        super.onMethodEnter();
    }

    @Override
    protected void onMethodExit(int opcode) {
        if (profiling() && ((IRETURN <= opcode && opcode <= RETURN) || opcode == ATHROW)) {
            mv.visitVarInsn(LLOAD, startTimeIdentifier);
            mv.visitLdcInsn(methodTagId);
            mv.visitMethodInsn(INVOKESTATIC, PROFILING_ASPECT_INNER_NAME, "profiling", "(JI)V", false);
        }

        super.onMethodExit(opcode);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        // 添加catch代码
        mv.visitLabel(to);
        mv.visitLabel(target);
        mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Exception"});

        int local = newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(ASTORE, local);
        mv.visitVarInsn(ALOAD, local);

        mv.visitVarInsn(ALOAD, local);
        mv.visitLdcInsn(methodTagId);
        mv.visitMethodInsn(INVOKESTATIC, PROFILING_ASPECT_EXCEPTION_INNER_NAME, "profiling", "(Ljava/lang/Throwable;I)V", false);
        mv.visitInsn(ATHROW);

        super.visitMaxs(maxStack, maxLocals);

    }

    private boolean profiling() {
        return methodTagId >= 0;
    }
}
