package de.upb.soot.signatures;

import static org.junit.Assert.*;

import de.upb.soot.namespaces.FileType;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import categories.Java8Test;

@Category(Java8Test.class)

public class SignatureFactoryTest {

  @Test
  public void getSamePackageSignature() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    PackageSignature packageSignature1 = signatureFactory.getPackageSignature("java.lang");
    PackageSignature packageSignature2 = signatureFactory.getPackageSignature("java.lang");
    boolean sameObject = packageSignature1 == packageSignature2;
    assertTrue(sameObject);
  }

  @Test
  public void eqPackageSignature() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    PackageSignature packageSignature1 = signatureFactory.getPackageSignature("java.lang");
    PackageSignature packageSignature2 = signatureFactory.getPackageSignature("java.lang");
    boolean sameObject = packageSignature1.equals(packageSignature2);
    assertTrue(sameObject);
  }

  @Test
  public void eqPackageSignature2() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    PackageSignature packageSignature1 = signatureFactory.getPackageSignature("java.lang");
    PackageSignature packageSignature2 = signatureFactory.getPackageSignature("java.lang");
    boolean sameObject = packageSignature1.equals(null);
    assertFalse(sameObject);
  }

  @Test
  public void eqPackageSignature3() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    JavaClassSignature classSignature1 = signatureFactory.getClassSignature("System", "java.lang");
    JavaClassSignature classSignature2 = signatureFactory.getClassSignature("System", "java.lang");
    PackageSignature packageSignature1 = classSignature1.packageSignature;
    PackageSignature packageSignature2 = classSignature2.packageSignature;
    boolean sameObject = packageSignature1.equals(packageSignature2);
    assertTrue(sameObject);
  }

  @Test
  public void getDiffPackageSignature() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    PackageSignature packageSignature1 = signatureFactory.getPackageSignature("java.lang");
    PackageSignature packageSignature2 = signatureFactory.getPackageSignature("java.lang.invoke");
    boolean sameObject = packageSignature1 == packageSignature2;
    assertFalse(sameObject);
  }

  @Test
  public void getClassSignature() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    JavaClassSignature classSignature1 = signatureFactory.getClassSignature("System", "java.lang");
    JavaClassSignature classSignature2 = signatureFactory.getClassSignature("System", "java.lang");
    // Class Signatures are unique but not their package
    boolean sameObject = classSignature1 == classSignature2;
    assertFalse(sameObject);
  }

  @Test
  public void getCompareClassSignature2() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    JavaClassSignature classSignature1 = signatureFactory.getClassSignature("System", "java.lang");
    JavaClassSignature classSignature2 = signatureFactory.getClassSignature("System", "java.lang");
    // Class Signatures are unique but not their package
    assertEquals(classSignature1, classSignature2);
  }

  @Test
  public void getCompareClassSignature3() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    JavaClassSignature classSignature1 = signatureFactory.getClassSignature("System", "java.lang");
    JavaClassSignature classSignature2 = classSignature1;
    // Class Signatures are unique but not their package
    boolean sameObject = classSignature1.equals(classSignature2);
    assertTrue(sameObject);
  }

  @Test
  public void getCompareClassSignature() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    JavaClassSignature classSignature1 = signatureFactory.getClassSignature("System", "java.lang");
    JavaClassSignature classSignature2 = null;
    // Class Signatures are unique but not their package
    boolean sameObject = classSignature1.equals(classSignature2);
    assertFalse(sameObject);
  }

  @Test
  public void sigToPath() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    JavaClassSignature classSignature1 = signatureFactory.getClassSignature("System", "java.lang");
    Path path = classSignature1.toPath(FileType.CLASS);
    // Class Signatures are unique but not their package
    assertEquals(path.toString(), "java/lang/System.class");
  }

  @Test
  public void sigFromPath() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    Path p = Paths.get("java/lang/System.class");
    JavaClassSignature classSignature = signatureFactory.fromPath(p);
    assertEquals(classSignature.toString(), "java.lang.System");
  }

  @Test
  public void getClassSignatureEmptyPackage() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    JavaClassSignature classSignature1 = signatureFactory.getClassSignature("A", "");
    JavaClassSignature classSignature2 = signatureFactory.getClassSignature("A");
    // Class Signatures are unique but not their package
    boolean sameObject = classSignature1 == classSignature2;
    assertFalse(sameObject);

    boolean samePackageSignatureObject = classSignature1.packageSignature == classSignature2.packageSignature;
    assertTrue(samePackageSignatureObject);
    String className = "A";

    assertEquals(classSignature1.toString(), className);
    assertEquals(classSignature2.toString(), className);
  }

  @Test
  public void getClassSignatureFullyQualified() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    JavaClassSignature classSignature1 = signatureFactory.getClassSignature("java.lang.System");
    JavaClassSignature classSignature2 = signatureFactory.getClassSignature("System", "java.lang");
    // Class Signatures are unique but not their package
    boolean sameObject = classSignature1 == classSignature2;
    assertFalse(sameObject);
  }

  @Test
  public void getClassSignaturesPackage() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    JavaClassSignature classSignature1 = signatureFactory.getClassSignature("System", "java.lang");
    JavaClassSignature classSignature2 = signatureFactory.getClassSignature("System", "java.lang");
    // Class Signatures are unique but not their package
    boolean samePackageSignature = classSignature1.packageSignature == classSignature2.packageSignature;
    assertTrue(samePackageSignature);

    // but they are equal
    assertEquals(classSignature1, classSignature2);
    assertEquals(classSignature1.hashCode(), classSignature2.hashCode());
  }

  @Test
  public void getMethodSignature() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    JavaClassSignature declClass = signatureFactory.getClassSignature("System", "java.lang");
    JavaClassSignature parameter = signatureFactory.getClassSignature("java.lang.Class");
    JavaClassSignature returnType = signatureFactory.getClassSignature("java.lang.A");

    List<String> parameters = Collections.singletonList("java.lang.Class");

    MethodSignature methodSignature
        = signatureFactory.getMethodSignature("foo", "java.lang.System", "java.lang.A", parameters);
    assertEquals(declClass, methodSignature.declClassSignature);
    assertEquals(returnType, methodSignature.returnTypeSignature);
    assertEquals(parameter, methodSignature.parameterSignatures.get(0));
  }

  @Test
  public void getMethodSignatureString() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();

    List<String> parameters = Collections.singletonList("java.lang.Class");

    MethodSignature methodSignature
        = signatureFactory.getMethodSignature("foo", "java.lang.System", "java.lang.A", parameters);
    assertEquals("<java.lang.System:java.lang.A foo(java.lang.Class)>", methodSignature.toString());
  }

  @Test
  public void getMethodSignatureString2() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();

    List<String> parameters = Collections.singletonList("java.lang.Class");

    MethodSignature methodSignature = signatureFactory.getMethodSignature("foo", "java.lang.System", "void", parameters);
    assertEquals("<java.lang.System:void foo(java.lang.Class)>", methodSignature.toString());
  }

  @Test
  public void getMethodSignatureString3() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();

    List<String> parameters = Collections.emptyList();

    MethodSignature methodSignature = signatureFactory.getMethodSignature("foo", "java.lang.System", "void", parameters);
    assertEquals("<java.lang.System:void foo()>", methodSignature.toString());
  }

  @Test
  public void getMethodSignatureString4() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();

    List<String> parameters = Collections.emptyList();
    JavaClassSignature classSignature = signatureFactory.getClassSignature("java.lang.System");
    MethodSignature methodSignature = signatureFactory.getMethodSignature("foo", classSignature, "void", parameters);
    assertEquals("<java.lang.System:void foo()>", methodSignature.toString());
    assertSame(methodSignature.declClassSignature, classSignature);
  }

  @Test
  public void compMethodSignature2() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();

    List<String> parameters = new ArrayList<>();

    MethodSignature methodSignature = signatureFactory.getMethodSignature("foo", "java.lang.System", "void", parameters);
    parameters.add("boolean");
    MethodSignature methodSignature2 = signatureFactory.getMethodSignature("foo", "java.lang.System", "void", parameters);

    assertNotEquals(methodSignature, methodSignature2);
    assertNotEquals(methodSignature.hashCode(), methodSignature2.hashCode());
  }

  @Test
  public void compMethodSignature1() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();

    List<String> parameters = Collections.emptyList();

    MethodSignature methodSignature = signatureFactory.getMethodSignature("foo", "java.lang.System", "void", parameters);
    MethodSignature methodSignature2 = signatureFactory.getMethodSignature("foo", "java.lang.System", "void", parameters);

    assertEquals(methodSignature, methodSignature2);
    assertEquals(methodSignature.hashCode(), methodSignature2.hashCode());
  }

  @Test
  public void getTypeSignature() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    JavaClassSignature classSignature1 = signatureFactory.getClassSignature("System", "java.lang");
    TypeSignature classSignature2 = signatureFactory.getTypeSignature("java.lang.System");
    assertEquals(classSignature1, classSignature2);
  }

  @Test
  public void getTypeSignatureTypes() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();

    TypeSignature byteSig = signatureFactory.getTypeSignature("byte");
    assertSame(byteSig, PrimitiveTypeSignature.BYTE_TYPE_SIGNATURE);
    assertSame("byte", byteSig.toString());

    TypeSignature shortSig = signatureFactory.getTypeSignature("SHORT");
    assertSame(shortSig, PrimitiveTypeSignature.SHORT_TYPE_SIGNATURE);
    assertSame("short", shortSig.toString());

    TypeSignature intSig = signatureFactory.getTypeSignature("int");
    assertSame(intSig, PrimitiveTypeSignature.INT_TYPE_SIGNATURE);
    assertSame("int", intSig.toString());

    TypeSignature longSig = signatureFactory.getTypeSignature("loNg");
    assertSame(longSig, PrimitiveTypeSignature.LONG_TYPE_SIGNATURE);
    assertSame("long", longSig.toString());

    TypeSignature floatSig = signatureFactory.getTypeSignature("floAt");
    assertSame(floatSig, PrimitiveTypeSignature.FLOAT_TYPE_SIGNATURE);
    assertSame("float", floatSig.toString());

    TypeSignature doubleSig = signatureFactory.getTypeSignature("doUble");
    assertSame(doubleSig, PrimitiveTypeSignature.DOUBLE_TYPE_SIGNATURE);
    assertSame("double", doubleSig.toString());

    TypeSignature charSig = signatureFactory.getTypeSignature("chaR");
    assertSame(charSig, PrimitiveTypeSignature.CHAR_TYPE_SIGNATURE);
    assertSame("char", charSig.toString());

    TypeSignature boolSig = signatureFactory.getTypeSignature("boolean");
    assertSame(boolSig, PrimitiveTypeSignature.BOOLEAN_TYPE_SIGNATURE);
    assertSame("boolean", boolSig.toString());

    TypeSignature nullSig = signatureFactory.getTypeSignature("nuLl");
    assertSame(nullSig, NullTypeSignature.NULL_TYPE_SIGNATURE);
    assertSame("null", nullSig.toString());

    TypeSignature voidSig = signatureFactory.getTypeSignature("void");
    assertSame(voidSig, VoidTypeSignature.VOID_TYPE_SIGNATURE);
    assertSame("void", voidSig.toString());

  }

  @Test(expected = NullPointerException.class)
  public void checkNullPackage() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    PackageSignature packageSignature = signatureFactory.getPackageSignature(null);
  }

  @Test(expected = NullPointerException.class)
  public void checkNullPackage2() {
    SignatureFactory signatureFactory = new DefaultSignatureFactory();
    JavaClassSignature classSignature = signatureFactory.getClassSignature("A", null);
  }
}
