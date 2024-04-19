(defproject boxing-alerts "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async "1.5.648"]
                 [org.craigandera/dynne "0.4.1"]]
  :plugins [[jonase/eastwood "1.2.5"]]
  :repl-options {:init-ns boxing-alerts.core}
  :main ^{:skip-aot true} boxing-alerts.core)