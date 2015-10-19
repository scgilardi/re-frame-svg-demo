(ns re-frame-svg-demo.core
  (:require
   [re-frame-svg-demo.handlers]
   [re-frame-svg-demo.subs]
   [re-frame-svg-demo.views :as v]
   [re-frame.core :as f]
   [reagent.core :as r]))

(enable-console-print!)

(defn mount-root []
  (r/render [v/main-panel]
            (.getElementById js/document "app")))

(defn ^:export init []
  (f/dispatch-sync [:initialize-db])
  (mount-root))
