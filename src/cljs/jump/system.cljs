(ns jump.system
  (:require [jump.system.player :as player]
            [jump.system.physics :as phys]))

(enable-console-print!)

(defn start []
  (player/bind-controls))

(defn update! [world]
  (let [new-world (->> @world
                      (phys/gravity)
                      (phys/move @player/input-cmd))]
    (player/clear-cmd)
    (when-not (= @world new-world)
      (reset! world new-world))))
