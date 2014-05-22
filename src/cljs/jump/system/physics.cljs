(ns jump.system.physics
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [jump.entity :as ent]
            [cljs.core.async :refer [<!]]))

;; Filters

(defn moveable?
  [entity]
  (let [has-position (ent/trait :position entity)
        takes-input (ent/trait :input entity)
        can-jump (ent/trait :jump entity)
        can-walk (ent/trait :walk entity)]
    (and has-position takes-input
         (or can-jump can-walk))))

(defn falls?
  [entity]
  (let [has-position (ent/trait :position entity)
        has-weight (ent/trait :gravity entity)]
    ; need an ascending flag for jumping
    (and has-position has-weight)))

;; Actions

(defn walk? [cmd]
  (#{:left :right} cmd))

(defn walk [entity cmd]
  (let [{:keys [x]} (ent/trait :position entity)
        {:keys [step facing]} (ent/trait :walk entity)
        op (if (= cmd :left) - +)
        update (-> entity
                    (assoc-in [:traits :position :x] (op x step))
                    (assoc-in [:traits :walk :facing] cmd))]
    (merge entity update)))

(defn jump [entities]
  {})

(defn fall [entities]
  {})

(defn merge-updates
  [game updates]
  (let [ids-match? #(= (:id %1) (:id %2))]
    (vec (for [e game u updates :when (ids-match? e u)]
           {:id (:id e)
            :traits (merge (:traits e) (:traits u))}))))

(defn move [input game]
  (when-let [moveables (filter moveable? game)]
    (go
      (let [cmd (<! input)
            updates (cond
                     (walk? cmd) (map #(walk % cmd) moveables)
                     (= :jump cmd) moveables)]
        (merge-updates game updates)))))

(defn gravity [game]
  (let [entities (filter falls? game)]
    ; apply gravity
    ; effect game
    ))
