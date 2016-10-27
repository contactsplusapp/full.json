(ns full.json-test
  #?(:clj (:require
            [clojure.test :refer :all]
            [full.json :as json]
            [full.core.sugar :refer [dq]]
            [clj-time.core :as time]
            [camelsnake.core :refer [->snake_case ->kebab-case-keyword]])
     :cljs (:require
             [cljs.test :refer-macros [deftest is]]
             [full.json :as json]
             [camel-snake-kebab.core :refer [->snake_case ->kebab-case-keyword]]
             [full.core.sugar :refer [dq]])))


(deftest test-read-json
  (is (= (json/read-json (dq "{'testStr':'Abc'}")) {:test-str "Abc"}))
  (is (= (json/read-json (dq "['1', '2', '3', '4']")) ["1" "2" "3" "4"]))
  (is (= (json/read-json (dq "[{'a':'b'}]")) [{:a "b"}])))

(deftest test-write-json
  (is (= (json/write-json {:test-str "Abc"}) (dq "{'testStr':'Abc'}")))
  (is (= (json/write-json {:test-str "Abc"} :json-key-fn nil)
         (dq "{'test-str':'Abc'}")))
  (is (= (json/write-json {:test-str "Abc"} :json-key-fn ->snake_case)
         (dq "{'test_str':'Abc'}"))))

#?(:clj
   (deftest test-write-json-date
     (is (= (json/write-json {:test-date (time/date-time 2014 1 2 3 4 5 678)})
            (dq "{'testDate':'2014-01-02T03:04:05.678Z'}")))))

(deftest test-convert-keys
  (is (= (json/convert-keys {"foo"       {"bar" 5}
                             "NaNoWriMo" {"fooBar" 3}
                             "XHTML"     3
                             "fooBarBaz" 5}
                            ->kebab-case-keyword
                            ["NaNoWriMo" "XHTML"])
         {:foo          {:bar 5}
          :na-no-wri-mo {"fooBar" 3}
          :xhtml        3
          :foo-bar-baz  5})))

(deftest test-preserve-header-casing
  (is (= (json/read-json (dq "{'headers':{'Sav27MAVhP': {'If-Match':'deadbeef'}}, 'data':{'notes':'I am a note'}}")
                         :preserve-keys ["headers"])
         {:headers {"Sav27MAVhP" {"If-Match" "deadbeef"}}
          :data {:notes "I am a note"}}))
  (is (= (json/read-json (dq "[{'headers':{'Sav27MAVhP': {'If-Match':'deadbeef'}}, 'data':{'notes':'I am a note'}}]")
                         :preserve-keys ["headers"])
         [{:headers {"Sav27MAVhP" {"If-Match" "deadbeef"}}
           :data    {:notes "I am a note"}}])))
