package com.example.simplenote.presentation.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simplenote.data.remote.NoteApi
import com.example.simplenote.domain.model.Note
import retrofit2.HttpException
import java.io.IOException

class NotePagingSource(
    private val api: NoteApi,
    private val query: String
) : PagingSource<Int, Note>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Note> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val response = if (query.isBlank()) {
                api.getNotes(page, pageSize)
            } else {
                api.filterNotes(query, page, pageSize)
            }

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.next == null) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Note>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
