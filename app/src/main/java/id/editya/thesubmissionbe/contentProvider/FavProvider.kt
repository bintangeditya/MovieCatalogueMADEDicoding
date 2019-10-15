package id.editya.thesubmissionbe.contentProvider

import android.content.*
import android.database.Cursor
import android.net.Uri
import id.editya.thesubmissionbe.data.local.BeDataBase
import java.util.ArrayList

class FavProvider : ContentProvider() {

    val AUTHORITY = "id.editya.thesubmissionbe"
    val URI_MENU = Uri.parse("content://$AUTHORITY/fav_movie")

    private val CODE_MENU_DIR = 1
    private val CODE_MENU_ITEM = 2

    private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

    init {
        MATCHER.addURI(AUTHORITY, "fav_movie", CODE_MENU_DIR)
        MATCHER.addURI(AUTHORITY, "fav_movie" + "/*", CODE_MENU_ITEM)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val code = MATCHER.match(uri)
        if (code == CODE_MENU_DIR || code == CODE_MENU_ITEM) {
            val context = context ?: return null
            val menu = BeDataBase.getInstance(context).movieDao()
            val cursor: Cursor
            cursor = if (code == CODE_MENU_DIR) {
                menu.selectAll()
            } else {
                menu.selectById(ContentUris.parseId(uri))
            }
            cursor.setNotificationUri(context.contentResolver, uri)
            return cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        return when (MATCHER.match(uri)) {
            CODE_MENU_DIR -> "vnd.android.cursor.dir/$AUTHORITY.fav_movie"
            CODE_MENU_ITEM -> "vnd.android.cursor.item/$AUTHORITY.fav_movie"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun applyBatch(operations: ArrayList<ContentProviderOperation>): Array<ContentProviderResult?> {

        val context = context ?: return arrayOfNulls(0)
        val database = BeDataBase.getInstance(context)
        database.beginTransaction()
        try {
            val result = super.applyBatch(operations)
            database.setTransactionSuccessful()
            return result
        } finally {
            database.endTransaction()
        }
    }
}