package org.coops;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MethodCallAnalyzer {

    private Map<String, Set<String>> methodCallMap = new HashMap<>();

    public MethodCallAnalyzer() {
        methodCallMap = new HashMap<>();
    }

    public Map<String, Set<String>> analyzeProject(String projectPath) throws IOException {
        Files.walk(Paths.get(projectPath))
                .filter(path -> path.toString().endsWith(".class"))
                .forEach(this::analyzeClass);

        return methodCallMap;
    }

    private void analyzeClass(Path classPath) {
        try {
            ClassReader classReader = new ClassReader(Files.newInputStream(classPath));
            classReader.accept(new ClassVisitor(Opcodes.ASM9) {
                private String currentClassName;

                @Override
                public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                    currentClassName = name;
                }

                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    String methodName = currentClassName + "." + name + descriptor;

                    return new MethodVisitor(Opcodes.ASM9) {
                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            String calledMethod = owner + "." + name + descriptor;
                            methodCallMap.computeIfAbsent(methodName, k -> new HashSet<>()).add(calledMethod);
                        }
                    };
                }
            }, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
