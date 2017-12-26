(ns my-exercise.test
  (:require [clojure.test :refer [deftest is testing run-tests]]
            [my-exercise.util :as util]))

(deftest keys-to-keyword-test
  (testing "keys-to-keyword util function"
    (let [input {"test" 1 "test2" 2}
          output {:test 1 :test2 2}]
      (is (= (util/keys-to-keywords input) output)))))

;; TODO test other util functions
;; TODO integrate with clojure spec
;; TODO prettify search results with CSS
;; TODO enrich OCD data

;;(run-tests)
