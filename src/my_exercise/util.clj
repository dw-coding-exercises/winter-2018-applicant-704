(ns my-exercise.util
  (:require [clojure.string :as string]))

(defn keys-to-keywords
  "transforms keys of a hashmap to keywords"
  [params]
  (->> params
     (map #(vector (keyword (first %)) (second %)))
     (into {})))

(defn- build-ocd-param-internal
  "transforms state city and country to ocd query param"
  ([state city country]
   (letfn [(ocd-pair [k v] (str k ":" v))
           (ocd-map [ks vs] (map ocd-pair ks vs))
           (make-param [data] (->> data
                                   (cons 'ocd-division)
                                   (string/join "/")))]
     (str (make-param (ocd-map '(country state)
                               [country state]))
          ","
          (make-param (ocd-map '(country state place)
                               [country state city]))))))

(defn build-ocd-param
  "take state and city and country make ocd param. country defaults to us.
this transforms the data and then calls `build-ocd-param-internal`"
  ([state city] (build-ocd-param state city "us"))
  ([state city country]
   (let [l-state (string/lower-case state)
         l-city (string/lower-case city)
         l-country (string/lower-case country)]
     (build-ocd-param-internal l-state l-city l-country))))
