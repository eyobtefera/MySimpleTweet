# Project 4 - *Eyob Tefera*

**MySimpleTwitter** is an android app that allows a user to view home and mentions timelines, view user profiles with user timelines, as well as compose and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **40** hours spent in total

## User Stories

The following **required** functionality is completed:
* [x] Includes all required user stories from Twitter Part 1.
* [x] User can switch between Timeline and Mention views using tabs. (3 points)
* [x] User can view their home timeline tweets.
* [x] User can view the recent mentions of their username.
* [x] User can compose tweets. See this conceptual guide for passing data into a timeline fragment.
* [x] User can navigate to view their own profile (2 points)
* [x] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [x] The users/verify_credentials endpoint can be used to access this information.
* [x] User can click on the profile image in any tweet to see another user's profile. (3 points)
* [x] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
* [x] Profile view should include that user's timeline
* [x] The users/show endpoint can be used to access this information.

The following **optional** features are implemented:

* [x] While composing a tweet, user can see a character counter with characters remaining for tweet out of 140
* [x] User can **pull down to refresh tweets** in either timeline.
* [x] Improve the user interface and theme the app to feel twitter branded with colors and styles
* [x] User can **search for tweets matching a particular query** and see results.
* [ ] When a network request is sent, user sees an [indeterminate progress indicator](http://guides.codepath.com/android/Handling-ProgressBars#progress-within-actionbar)
* [x] User can **"reply" to any tweet on their home timeline**
* [x] The user that wrote the original tweet is automatically "@" replied in compose
* [x] User can click on a tweet to be **taken to a "detail view"** of that tweet
* [x] User can take favorite (and unfavorite) or retweet actions on a tweet
* [ ] User can see embedded image media within the tweet item in list or detail view.
* [ ] Compose activity is replaced with a modal compose overlay.
* [x] User can **click a link within a tweet body** on tweet details view. The click will launch the web browser with relevant page opened.
* [x] Used Parcelable instead of Serializable leveraging the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler) when passing data between activities.
* [ ] Replaced all icon drawables and other static image assets with [vector drawables](http://guides.codepath.com/android/Drawables#vector-drawables) where appropriate.
* [ ] User can view following / followers list through the profile of a user
* [ ] Apply the popular Butterknife annotation library to reduce view boilerplate.
* [ ] Implement collapse scrolling effects on the Twitter profile view using `CoordinatorLayout`.
* [ ] User can **open the twitter app offline and see last loaded tweets**. Persisted in SQLite tweets are refreshed on every application launch. While "live data" is displayed when app can get it from Twitter API, it is also saved for use in an offline mode.

The following **additional** features are implemented:

* [ ] User can view more tweets as they scroll with [infinite pagination](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView). Number of tweets is unlimited.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright [2017] [Eyob Tefera]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
