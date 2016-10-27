(defproject fullcontact/full.json "0.10.2-SNAPSHOT"
  :description "Read and write JSON (Cheshire extension)."
  :url "https://github.com/fullcontact/full.json"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo}
  :deploy-repositories [["releases" {:url "https://clojars.org/repo/" :creds :gpg}]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [cheshire "5.5.0"]
                 [fullcontact/camelsnake "0.9.0"]
                 [fullcontact/full.core "0.10.2"]]
  :aliases {"at" ["test-refresh"]}
  :aot :all
  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version" "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag" "--no-sign"]
                  ["deploy"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]]
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.16.0"]]}})