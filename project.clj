(defproject re-frame-svg-demo "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.145"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [reagent "0.5.1"]
                 [re-frame "0.5.0-alpha2"]
                 [figwheel "0.2.6"]
                 [re-com "0.6.1"]]

  :source-paths ["src/clj"]

  :plugins [[lein-cljsbuild "1.1.0"]
            [lein-figwheel "0.3.8" :exclusions [cider/cider-nrepl]]  ]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]

                        :figwheel {:on-jsload "re-frame-svg-demo.core/mount-root"}

                        :compiler {:main re-frame-svg-demo.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :source-map-timestamp true}}

                       {:id "min"
                        :source-paths ["src/cljs"]
                        :compiler {:main re-frame-svg-demo.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :optimizations :advanced
                                   :pretty-print false}}]}
  :aliases
  {"fw" ["do" "clean," "figwheel" "dev"]
   "release" ["do" "clean," "cljsbuild" "once" "min"]})
