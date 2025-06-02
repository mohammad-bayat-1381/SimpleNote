package com.example.simplenote.presentation.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simplenote.data.remote.NoteApi
import com.example.simplenote.domain.model.Note

class NotePagingSource(
    private val api: NoteApi,
    private val searchQuery: String
) : PagingSource<Int, Note>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Note> {
        val page = params.key ?: 1
        val pageSize = params.loadSize.coerceAtMost(10)  // Match server page size

        return try {
            val response = if (searchQuery.isNotBlank()) {
                api.filterNotes(
                    searchQuery = searchQuery,
                    page = page,
                    pageSize = pageSize
                )
            } else {
                api.getNotes(
                    page = page,
                    pageSize = pageSize
                )
            }

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.next != null) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Note>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}
