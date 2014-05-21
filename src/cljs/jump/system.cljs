(ns jump.system
  (:require [cljs.core.async :refer [sliding-buffer]]
            [jump.player :as player]
            [jump.physics :as phys]))

; input command channel
; NOTE might need (unique 1)
(def cmd-chan (sliding-buffer 1))

(defn start []
  (player/bind-controls cmd-chan))

(defn update! [game]
  (phys/move cmd-chan game)
  @game)
