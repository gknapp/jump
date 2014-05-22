(ns jump.system
  (:require [jump.system.player :as player]
            [jump.system.physics :as phys]))

(enable-console-print!)

(defn start []
  (player/bind-controls))

(defn update! [game]
  (let [new-game (->> @game
                      (phys/move player/input-cmd))]
    (player/clear-cmd)
    #_(println "system/update!_updated" updated)
    #_(reset! game new-game)
    @game))
