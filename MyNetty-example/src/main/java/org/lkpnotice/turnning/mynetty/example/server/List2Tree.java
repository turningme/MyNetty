package org.lkpnotice.turnning.mynetty.example.server;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * Created by liujinpeng on 2019/5/12.
 */
public class List2Tree {


    /**
     * 输入数据
     * @return
     */
    public static List<User> getInputData(){
        List<User>  users = new ArrayList();
        users.add(new User(2L,"user2",3L));
        users.add(new User(5L,"user5",6L));
        users.add(new User(6L,"user6",3L));
        users.add(new User(3L,"user3",-1L));

        return users;
        //return Collections.emptyList();
    }


    /**
     * 用户列表转tree的主要方法
     * @param userList
     * @return
     */
    public Node list2Tree(List<User> userList){
        if (null == userList || userList.size() <=0){
            return null;
        }



        Node root = null;
        Map<Long,Node> findMap = new HashMap();
        List<Long> unBinded = new LinkedList();

        //合法的列表
        for (User u:userList
             ) {
            Node n = new Node();
            //user信息到Node中 复制
            n.fromUser(u);

            //根节点?
            if (-1 == u.getParentId()){
                n.parent = null;
                root = n;
                findMap.put(n.getId(),n);
            }else{//不是根节点

                //查找parent
                Node p = findMap.get(n.getParentId());

                if (null == p){//parent 不存在
                    unBinded.add(n.getId());
                }else{//parent存在，建立和parent的引用关系
                    n.parent = p;
                    p.children.add(n);
                }

                findMap.put(n.getId(),n);
            }
        }


        //上述建立了一颗不完整的tree ，有一些 child - parent 关系没有联系上
        //将失去联系的内容加上

        for (Long e:unBinded
             ) {
            Node nn = findMap.get(e);
            Node p = findMap.get(nn.getParentId());

            if (null != p){
                nn.parent = p;
                p.children.add(nn);
            }else{
                throw  new IllegalArgumentException("node nn find no parent {} {}");
            }

        }

        return root;

    }


    public static void main(String[] args){
        List<User> input = getInputData();
        List2Tree list2Tree = new List2Tree();
        Node tree = list2Tree.list2Tree(input);
        //output tree
        System.out.println(JSON.toJSONString(tree));
    }



    static class Node extends User{


        List<Node> children = new ArrayList();
        Node parent = null;

        public Node() {
            super();
        }

        public Node(long id, String name, long parentId, List<Node> children, Node parent) {
            super(id, name, parentId);
            this.children = children;
            this.parent = parent;
        }

        public void fromUser(User user){
            //
            setId(user.getId());
            setParentId(user.getParentId());
            setName(user.getName());
        }
    }

    /**
     * 用户
     */
    static class User{
        long id;
        String name;
        long parentId;

        public User() {
        }

        public User(long id, String name, long parentId) {
            this.id = id;
            this.name = name;
            this.parentId = parentId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getParentId() {
            return parentId;
        }

        public void setParentId(long parentId) {
            this.parentId = parentId;
        }
    }
}
