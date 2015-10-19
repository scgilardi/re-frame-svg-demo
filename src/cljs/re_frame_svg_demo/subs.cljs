(ns re-frame-svg-demo.subs
  (:require
   [re-frame.core :as f]
   [reagent.ratom :refer-macros [reaction]]))

(f/register-sub
 :name
 (fn [db]
   (reaction (:name @db))))

(f/register-sub
 :items
 (fn [db]
   (reaction (:items @db))))

(f/register-sub
 :drag
 (fn [db]
   (reaction (:drag @db))))
