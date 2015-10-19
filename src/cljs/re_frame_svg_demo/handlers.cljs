(ns re-frame-svg-demo.handlers
  (:require
   [cljs.core.match :refer-macros [match]]
   [re-frame.core :as f]))

(def default-db
  {:name "re-frame-svg-demo"
   :next-id 1001
   :items []})

(f/register-handler
 :initialize-db
 (fn  [_ _]
   default-db))

(f/register-handler
 :mouse-event
 (fn [{:keys [next-id drag] :as db} [_ event]]
   (let [{:keys [type pos buttons modifiers]} event
         button-state (if drag :was-down :was-up)]
     (match [type button-state]
            [:mousedown :was-up]
            (assoc db :drag {:start pos :delta [0 0]})
            [:mousemove :was-down]
            (let [[x0 y0] (:start drag)
                  [x y] pos
                  delta [(- x x0) (- y y0)]]
              (update-in db [:drag] assoc :delta delta))
            [:mouseup :was-down]
            (do
              (f/dispatch [:create-item drag])
              (dissoc db :drag db))
            [_ _]
            db))))

(f/register-handler
 :create-item
 (fn [{:keys [next-id] :as db} [_ {:keys [start delta]}]]
   (let [item {:id (keyword (str "i" next-id))
               :pos start
               :size delta}]
     (-> db
         (update-in [:next-id] inc)
         (update-in [:items] conj item)))))

(f/register-handler
 :clear-items
 (fn [db [_]]
   (assoc db :items [])))
