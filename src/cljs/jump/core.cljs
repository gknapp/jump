(ns jump.core
  (:require [jump.components :as component]
   [jump.ui :refer [request-frame render]]))

(enable-console-print!)

(def game
  {:entities
   [{:id :player
     :traits [(component/position 50 565)
              (component/walk 4 :right)
              (component/jump 60)
              (component/gravity 10)
              (component/control :keyboard)
              (component/mortal)
              (component/solid)
              (component/renderable 37 35)]}]})

(defn update! [game]
  game)

(defn animate []
  (-> game
      update!
      render)
  (request-frame animate))

(set! (.-onload js/window) animate)
