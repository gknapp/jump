(ns jump.system
  (:require [jump.system.player :as player]
            [jump.system.physics :as phys]))

(enable-console-print!)

(defn start []
  (player/bind-controls))

(defn update! [world]
  (let [player-cmds @player/commands
        new-world (->> @world
                      (phys/gravity)
                      (phys/walk player-cmds)
                      (phys/jump player-cmds))]
    (when-not (= @world new-world)
      (reset! world new-world))))
