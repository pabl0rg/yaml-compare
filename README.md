This program helps compare yaml files (ansible group_vars) to check, for example if the same ip and port are assigned to two instances

### Usage

```java -jar yaml-compare-0.1.jar <group-by> <print> [yaml files]```

Keys use dot notation to identify nodes in the yaml tree.  * is wildcard and will match any value

For example if we have these two yaml files in group_vars

```
instances:
    dev:
        ip: 1.1.1.1
        ports:
            memcache: 12345
            web: 8080
```

```
instances:
    prod:
        ip: 1.1.1.1
        ports:
            memcache: 12345
            web: 80
```

### Collision-check mode

```java -jar yaml-compare-0.1.jar -g instances.*.ip -c instances.*.ports.* -f group_vars/*```

Output

```
-----
group:1.1.1.1
	COLLISION instances.*.ports.memcache: 12345
		src/test/resources/a.yaml
		src/test/resources/b.yaml
```

### Verbose mode

```java -jar yaml-compare-0.1.jar -g instances.*.ip -c instances.*.ports.* -f group_vars/* -v```

Output

```
loading: src/test/resources/a.yaml
loading: src/test/resources/b.yaml
-----
	1.1.1.1
		src/test/resources/a.yaml: 8080
		src/test/resources/b.yaml: 80
	1.1.1.1
		src/test/resources/a.yaml: 12345
		src/test/resources/b.yaml: 12345
```