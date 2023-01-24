package co.za.lungisani.service;

public interface GenericService<T, K, LT> {

    T retrieve(K key);
    LT retrieveAll();

}
