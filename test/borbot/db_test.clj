(ns borbot.db-test
  (:require [borbot.db :as bdb]
            [clojure.test :as t]))

(t/deftest random-cit-test
  (t/testing "Random cit test"
    (t/is (= (bdb/random-cit) "Brbrbrbrbbbbrrrbbbrbrbrbrbrb"))))
