## What is a Java POJO?
POJO stands for Plain Old Java Object. It is an ordinary Java object, not bound by any special restriction other than those forced by the Java Language Specification and not requiring any classpath. POJOs are used for increasing the readability and re-usability of a program. POJOs have gained the most acceptance because they are easy to write and understand. They were introduced in EJB 3.0 by Sun Microsystems.

They typically use private fields to encapsulate data, with public getter and setter methods to access and modify these fields.

## JavaBean
Beans are special type of Pojos. There are some restrictions on POJO to be a bean.

1. All JavaBeans are POJOs but not all POJOs are JavaBeans.
2. Serializable i.e. they should implement Serializable interface. Still, some POJOs who don't implement a Serializable interface are called POJOs because Serializable is a marker interface and therefore not of many burdens.
3. Fields should be private. This is to provide complete control on fields.
4. Fields should have getters or setters or both.
5. A no-arg constructor should be there in a bean.
6. Fields are accessed only by constructor or getter setters.
