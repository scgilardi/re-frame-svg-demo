(ns re-frame-svg-demo.events
  (:require
   [re-frame.core :as f]
   [reagent.core :as r]))

(defn mouse-event-handler
  []
  (let [component (r/current-component)]
    (fn [e]
      (.preventDefault e)
      (let [dom-node (r/dom-node component)
            bounds (.getBoundingClientRect dom-node)
            type (keyword (.-type e))
            x (- (.-clientX e) (.-left bounds))
            y (- (.-clientY e) (.-top bounds))
            pos [x y]
            modifiers (into #{} (remove nil?)
                            [(if (.-altKey e) :alt)
                             (if (.-ctrlKey e) :ctrl)
                             (if (.-metaKey e) :meta)
                             (if (.-shiftKey e) :shift)])
            button-bits (.-buttons e)
            buttons (into #{}
                          (comp
                           (map #(if (bit-test button-bits %) %))
                           (remove nil?))
                          (range 8))
            event {:type type
                   :pos pos
                   :buttons buttons
                   :modifiers modifiers}]
        (f/dispatch [:mouse-event event])))))
