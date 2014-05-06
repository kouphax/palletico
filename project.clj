(defproject palletico "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2173"]
                 [cljs-ajax "0.2.3"]
                 [reagent "0.4.2"]]

  :plugins [[lein-cljsbuild "1.0.2"]
            [lein-simpleton "1.3.0"]]

  :source-paths ["src"]

  :cljsbuild {
    :builds [{:id "palletico"
              :source-paths ["src"]
              :compiler { :output-to "palletico.js"
                          :output-dir "out"
                          :optimizations :none
                          :source-map true}}]})
