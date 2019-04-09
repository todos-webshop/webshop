package webshop;

public interface Validator {

    default boolean isEmpty(String str){
        return str == null || str.trim().isEmpty();
    }
}
