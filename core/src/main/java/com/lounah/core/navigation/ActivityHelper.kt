package com.lounah.core.navigation

import android.content.Intent

/**
 * Helpers to start activities in a modularized world.
 */

private const val PACKAGE_NAME = "com.lounah.poster"

/**
 * Create an Intent with [Intent.ACTION_VIEW] to an [AddressableActivity].
 */
fun intentTo(addressableActivity: AddressableActivity): Intent {
    return Intent(Intent.ACTION_VIEW).setClassName(
            PACKAGE_NAME,
            addressableActivity.className)
}

/**
 * An [android.app.Activity] that can be addressed by an intent.
 */
interface AddressableActivity {
    /**
     * The activity class name.
     */
    val className: String
}

/**
 * All addressable activities.
 *
 * Can contain intent extra names or functions associated with the activity creation.
 */
object Activities {

//    /**
//     * AboutActivity
//     */
//    object About : AddressableActivity {
//        override val className = "$PACKAGE_NAME.about.ui.AboutActivity"
//    }
//
//    /**
//     * Base object for DesignerNews activities.
//     */
//    object DesignerNews {
//        /**
//         * DesignerNews LoginActivity
//         */
//        object Login : AddressableActivity {
//            override val className = "$PACKAGE_NAME.designernews.ui.login.LoginActivity"
//        }
//
//        /**
//         * DesignerNewsStory Activity
//         */
//        object Story : AddressableActivity {
//            override val className = "$PACKAGE_NAME.designernews.ui.story.StoryActivity"
//            const val EXTRA_STORY_ID = "story_id"
//
//            /**
//             * Create the intent for this Activity's custom tab.
//             */
//            fun customTabIntent(
//                context: Context,
//                story: io.plaidapp.core.designernews.data.stories.model.Story,
//                session: CustomTabsSession?
//            ): CustomTabsIntent.Builder {
//                val upvoteStory = Intent(context, UpvoteStoryService::class.java)
//                upvoteStory.action = UpvoteStoryService.ACTION_UPVOTE
//                upvoteStory.putExtra(UpvoteStoryService.EXTRA_STORY_ID, story.id)
//                val pendingIntent = PendingIntent.getService(context, 0, upvoteStory, 0)
//
//                return CustomTabsIntent.Builder(session)
//                        .setToolbarColor(ContextCompat.getColor(context, R.color.designer_news))
//                        .setActionButton(drawableToBitmap(context,
//                                R.drawable.ic_upvote_filled_24dp_white)!!,
//                                context.getString(R.string.upvote_story),
//                                pendingIntent,
//                                false)
//                        .setShowTitle(true)
//                        .enableUrlBarHiding()
//                        .addDefaultShareMenuItem()
//            }
//        }
//    }
//
//    /**
//     * Base object for Dribbble activities.
//     */
//    object Dribbble {
//        /**
//         * ShotActivity
//         */
//        object Shot : AddressableActivity {
//            override val className = "$PACKAGE_NAME.dribbble.ui.shot.ShotActivity"
//
//            const val EXTRA_SHOT_ID = "EXTRA_SHOT_ID"
//            const val RESULT_EXTRA_SHOT_ID = "RESULT_EXTRA_SHOT_ID"
//        }
//    }
//
//    /**
//     * SearchActivity
//     */
//    object Search : AddressableActivity {
//        override val className = "$PACKAGE_NAME.search.ui.SearchActivity"
//
//        const val EXTRA_QUERY = "EXTRA_QUERY"
//        const val EXTRA_SAVE_DRIBBBLE = "EXTRA_SAVE_DRIBBBLE"
//        const val EXTRA_SAVE_DESIGNER_NEWS = "EXTRA_SAVE_DESIGNER_NEWS"
//        const val RESULT_CODE_SAVE = 7
//    }
}