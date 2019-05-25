package com.example.twittervision

object TwitterService {

    private const val SEARCH_END_POINT = "https://api.twitter.com/1.1/search/tweets.json"

    private fun constructEndPoint(
            query: String,
            latitude: Double,
            longitude: Double,
            searchRadiusInKm: Int
    ): String {
        return """
            $SEARCH_END_POINT
            ?q=$query
            &geocode=$latitude,$longitude,${searchRadiusInKm}km
            &result_type=recent
            &count=100
            """
    }

    fun executeSearch(){

    }

}