(ns jump.core
  (:require [jump.entity :refer [entity]]
            [jump.components :as component]
            [jump.system :as system]
            [jump.ui :as ui]))

(enable-console-print!)

(def game-state
  (atom
   [(entity :player
            (component/position 60 565)
            (component/walk 4 :right)
            (component/jump 60)
            (component/gravity 10)
            (component/input)
            (component/player)
            (component/mortal)
            (component/solid)
            (component/renderable 37 35))
    (entity (gensym "wall")
            (component/solid)
            (component/position 0 0)
            (component/renderable 20 600))
    (entity (gensym "wall")
            (component/solid)
            (component/position 780 0)
            (component/renderable 20 600))
    (entity (gensym "platform")
            (component/solid)
            (component/position 20 430)
            (component/renderable 600 20))
    (entity (gensym "platform")
            (component/solid)
            (component/position 260 260)
            (component/renderable 400 15))]))

(defn game-loop []
  (ui/request-frame game-loop)
  (-> game-state
      system/update!)
      ui/render)

(defn game-start []
  (println @game-state)
  (system/start)
  (game-loop))

(set! (.-onload js/window) game-start)
