(ns jump.system.physics
  (:require [jump.entity :as ent]))

;; Filters

(defn moveable?
  [entity]
  (let [has-position (ent/has-trait :position entity)
        takes-input (ent/has-trait :input entity)
        can-jump (ent/has-trait :jump entity)
        can-walk (ent/has-trait :walk entity)]
    (and has-position takes-input (or can-jump can-walk))))

(defn falls?
  [entity]
  (let [has-position (ent/has-trait :position entity)
        has-weight (ent/has-trait :gravity entity)]
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
  (let [ids-match? #(= (:id %1) (:id %2))
        new-game []
        new-game (for [e game u updates]
                   (conj new-game (if (ids-match? e u)
                                    {:id (:id e)
                                     :traits (merge (:traits e) (:traits u))}
                                    e)))]
    (println "new-game:" new-game)
    new-game))

(defn move [input game]
  (when-let [moveables (seq (filter moveable? game))]
    (let [cmd @input
          updates (cond
                   (walk? cmd) (map #(walk % cmd) moveables)
                   (= :jump cmd) nil)]
      (if-not (empty? updates)
        (merge-updates game updates)
        game))))

(defn gravity [game]
  (let [entities (filter falls? game)]
    ; apply gravity
    ; effect game
    ))
