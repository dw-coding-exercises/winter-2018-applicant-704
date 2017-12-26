(ns my-exercise.search
  (:require [hiccup.page :refer [html5]]
            [clj-http.client :as http]
            [my-exercise.util :as util]))

(def upcoming-endpoint "https://api.turbovote.org/elections/upcoming")
(def upcoming-query-param "district-divisions")

(defn get-elections
  "gets upcoming elections api response from turbovote"
  [response]
  (let [body (:body response)
        data (read-string body)]
    (map (juxt :description :date) data)))

(defn format-election
  "formats elections for hiccup"
  [[name date]]
  [:li
   [:h3 name]
   [:div "date: " date]])

(defn search-handler
  "search handler"
  [req]
  (let [keyword-params (util/keys-to-keywords (:form-params req))
        {:keys [street street-2 city state zip]} keyword-params
        res (http/get upcoming-endpoint
                      {:query-params {upcoming-query-param
                                      (util/build-ocd-param state city)}})
        elections (get-elections res)]
    (html5
     [:div
      [:h1 "Upcoming nearby elections"]
      (if (empty? elections)
        [:div "no upcoming nearby elections"]
        [:ul
         (map format-election elections)])])))

