package vn.theonestudio.surf.api;

@FunctionalInterface
public interface ApiCallExecutor<T> {
   ApiCallResult<T> call() throws ApiCallException;
}