This program helps compare yaml files (ansible group_vars) to check, for example if the same ip and port are assigned to two instances

### Usage

```java -jar yaml-compare-0.1.jar <group-by> <print> [yaml files]```

Keys use dot notation to identify nodes in the yaml tree.  * is wildcard and will match any value

For example if we have these two yaml files in group_vars

```
instances:
    dev:
        ip: 1.1.1.1
        port: 12345
```

```
instances:
    prod:
        ip: 1.1.1.1
        port: 12345
```

Running

```java -jar yaml-compare-0.1.jar instances.*.ip instances.*.port group_vars/*```

Prints

```
loading: group_vars/a.yml
loading: group_vars/b.yml
-----
instances.*.port:
    1.1.1.1
        group_vars/a.yml: 12345
        group_vars/b.yml: 12345
```
