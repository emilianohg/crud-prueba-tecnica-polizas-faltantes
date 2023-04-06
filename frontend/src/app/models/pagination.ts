export interface Pagination<T> {
    page: number,
    currentPage: number,
    totalPages: number,
    totalRecords: number,
    records: T[],
    limit: number
}