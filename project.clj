(defproject jump "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://github.com/gknapp/jump"
  :source-paths ["src/clj" "src/cljs"]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2202"]]
  :plugins [[lein-cljsbuild "1.0.3"]]
  :cljsbuild {:builds
              [{:source-paths ["src/cljs"]
                :compiler {:output-to "resources/public/js/jump/main.js"
                           :output-dir "resources/public/js/jump"
                           :optimizations :whitespace
                           :pretty-print true}}]})
