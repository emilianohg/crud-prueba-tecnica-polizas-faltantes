export interface BaseResponse<T> {
    meta: {
        status: string;
    },
    data: T;
}