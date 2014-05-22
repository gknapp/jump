(ns jump.system
  (:require [cljs.core.async :refer [chan]]
            [jump.system.player :as player]
            [jump.system.physics :as phys]))

; input command channel
(def cmd-chan (chan 1))

(defn start []
  (player/bind-controls cmd-chan))

(defn update! [game]
  (let [updated (->> @game
                     (phys/move cmd-chan))]
    (reset! game updated)
    @game))
