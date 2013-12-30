(defproject johncowie-design "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.4"]
                 [garden "1.1.4"]]
  :plugins [[lein-ring "0.8.8"]]
  :ring {:handler johncowie-design.handler/app}
  :main johncowie-design.handler
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
