package org.dows.rbac;

public class MethodParser {

    public static void main(String[] args) {

        //GenericTypeParser gtp = GenericTypeParser.parseGenericReturnType();

       /* String signature = "(Lorg/dows/rbac/api/admin/request/FindRbacGroupRequest;)Ljava/util/List<Lorg/dows/rbac/entity/RbacGroupEntity;>;";

        MethodSignature methodSignature = MethodSignatureParser.parse(signature);

        System.out.println("Parameters:");
        methodSignature.getParameters().forEach(p -> {
            System.out.println("  dataType: " + p.getDataType());
            if (p.isCollection()) {
                System.out.println("  collectionType: " + p.getCollectionType());
            }
        });

        System.out.println("\nReturn Type:");
        System.out.println("  dataType: " + methodSignature.getReturnType().getDataType());
        if (methodSignature.getReturnType().isCollection()) {
            System.out.println("  collectionType: " + methodSignature.getReturnType().getCollectionType());
        }

        System.out.println("\nComplete Signature Object:");
        System.out.println(methodSignature);*/
    }
}
