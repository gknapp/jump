(ns jump.system.physics
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [jump.entity :as ent]
            [cljs.core.async :refer [<!]]))

;; Filters

(defn moveable?
  [entity]
  (let [has-position (ent/trait :position entity)
        takes-input (ent/trait :input entity)
        can-walk (ent/trait :walk entity)]
    (and has-position takes-input can-walk)))

(defn falls?
  [entity]
  (let [has-position (ent/trait :position entity)
        has-weight (ent/trait :gravity entity)]
    ; need an ascending flag for jumping
    (and has-position has-weight)))

;; Actions

(defn walk [entities cmd]
  (doseq [entity entities]
    (let [{:keys [x step]} (ent/trait :position entity)
          mov (if (= cmd :left) - +)]
      ; needs to effect game
      (mov x step))))

(defn jump [entities]
  {})

(defn fall [entities]
  {})


(defn move [commands game]
  (go
    (let [entities (filter moveable? @game)
          cmd (<! commands)]
      (cond
       (#{:left :right} cmd) (walk entities cmd))
       ;swap! entities into game
      )))

(defn gravity [game]
  (let [entities (filter falls? game)]
    ; apply gravity
    ; effect game
    ))
