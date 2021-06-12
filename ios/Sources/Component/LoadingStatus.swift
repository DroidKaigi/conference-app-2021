public enum LoadingStatus<T> {
    case initial
    case loading
    case loaded(T)
    case error(Error)
}
