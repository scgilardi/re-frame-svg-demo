(ns re-frame-svg-demo.handlers
  (:require
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
     (case [type button-state]
       [:mousedown :was-up]
       (assoc db :drag {:start pos :pos pos})
       [:mousemove :was-down]
       (assoc db :drag (assoc drag :pos pos))
       [:mouseup :was-down]
       (do
         (f/dispatch [:cleanup-drag])
         (f/dispatch [:create-item (assoc drag :pos pos)])
         db)
       db))))

(f/register-handler
 :cleanup-drag
 (fn [db [_]]
   (dissoc db :drag)))

(f/register-handler
 :create-item
 (fn [{:keys [next-id] :as db} [_ {:keys [start pos]}]]
   (let [item {:id (keyword (str "i" next-id))
               :start start
               :pos pos}]
     (-> db
         (update-in [:next-id] inc)
         (update-in [:items] conj item)))))

(f/register-handler
 :clear-items
 (fn [db [_]]
   (assoc db :items [])))
