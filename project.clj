(defproject fullcontact/full.json "0.12.1"
  :description "Read and write JSON for Clojure and ClojureScript."
  :url "https://github.com/fullcontact/full.json"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo}
  :repositories [["fullcontact" {:url "https://contactsplus.jfrog.io/artifactory/repo"}]
                 ["releases" {:url "https://contactsplus.jfrog.io/artifactory/libs-release-local"}]
                 ["snapshots" {:url "https://contactsplus.jfrog.io/artifactory/libs-snapshot-local"}]]
  :deploy-repositories [["releases" {:url "https://contactsplus.jfrog.io/artifactory/libs-release-local" :sign-releases false}]
                        ["snapshots" {:url "https://contactsplus.jfrog.io/artifactory/libs-snapshot-local" :sign-releases false}]]
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.597"]
                 [cheshire "5.5.0"]
                 [fullcontact/camelsnake "0.10.0"]
                 [fullcontact/full.core "1.1.3"]
                 [camel-snake-kebab "0.4.0"]]
  :aliases {"at" ["test-refresh"]
            "ats" ["doo" "phantom"]}
  :aot :all
  :cljsbuild {:builds {:test {:source-paths ["src" "test"]
                              :compiler {:output-to "target/test.js"
                                         :main 'full.test-runner
                                         :optimizations :simple
                                         :pretty-print true}}}}
  :doo {:build "test"}
  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version" "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag" "--no-sign"]
                  ["deploy"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]]
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.16.0"]
                             [lein-doo "0.1.7"]]}})
