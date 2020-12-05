
## example

- rolelist.html
- roleform.html

#### 可以删除的 json


- src/main/resources/static/start/json/user/login.js 登录
- src/main/resources/static/start/json/user/logout.js 退出登录
- src/main/resources/static/start/json/user/session.js 获取信息

### 

- view.js

```
  //Ajax请求， type 不传的话 post 请求，type 为空的话，就是 get
  view.req = function(options, type){
```

- table.js  修改页码的选择的问题

```js

{elem:"layui-table-page"+s.index,count:o,limit:s.limit,limits:s.limits||[10,20,30,40,50,60,70,80,90]

```