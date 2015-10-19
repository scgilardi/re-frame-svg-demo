(ns re-frame-svg-demo.views
  (:require
   [re-com.core :as c]
   [re-frame-svg-demo.events :as e]
   [re-frame.core :as f]
   [reagent.core :as r]))

(defn title []
  (let [name (f/subscribe [:name])]
    (fn []
      [c/title
       :label (str "Hello from " @name)
       :level :level1])))

(defn rect
  [{:keys [id pos size]}]
  (let [[x y] pos
        [w h] size
        [left top] [(min x (+ x w)) (min y (+ y h))]
        [right bottom] [(max x (+ x w)) (max y (+ y h))]]
    [:rect {:key id
            :style {:fill "rgba(0,0,128,0.9)"
                    :stroke-width 3
                    :stroke :black}
            :x left
            :y top
            :width (- right left)
            :height (- bottom top)}]))

(defn svg-pane
  []
  (let [drag (f/subscribe [:drag])
        items (f/subscribe [:items])
        mouse-handler (e/mouse-event-handler)]
    (fn []
      [:svg {:style {:border "thin solid black"}
             :width 1000 :height 1000
             :on-mouse-up mouse-handler
             :on-mouse-down mouse-handler
             :on-mouse-move mouse-handler}
       (concat
        (when-let [{pos :start size :delta} @drag]
          [(rect {:id :creating
                  :pos pos
                  :size size})])
        (into [] (map rect @items)))])))

(defn clear-button []
  (let [items (f/subscribe [:items])]
    (fn []
      [c/button
       :label "Clear"
       :class "btn btn-primary"
       ;; :tooltip "Remove all components"
       ;; :tooltip-position :right-center
       :disabled? (empty? @items)
       :on-click #(f/dispatch [:clear-items])])))


(defn svg-controls []
  [c/v-box
   :gap "1em"
   :size "50px"
   :margin "10px"
   :align :center
   :children [[clear-button]]])

(defn svg-area []
  [c/h-box
   :gap "1em"
   :size "auto"
   :margin "10px"
   :children [[svg-controls]
              [svg-pane]]])

(defn main-panel []
  (fn []
    [c/v-box
     :height "100%"
     :children [[title]
                [svg-area]]]))
