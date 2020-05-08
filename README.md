## 一个使用Jetpack设计的MVP + MVVM开发模式

+ 支持标准MVP用法，Activity或者Fragment作为P，可配置DataBinder
+ 支持ViewModel + liveData的简易用法，可快速实现MVVM
+ 支持view复用
+ 支持AndroidX

### 如何引入
`implementation jzw.mvp:jetpmvp:1.0.1`


### 一、MVVM简易模式用法，使用ViewModel和LiveData处理数据
##### 1、创建数据模型model类，构造页面需要的所有数据，必须继承IModel接口
```
   public class StudentModel implements IModel {
           private List<Student> users;
           private String city;

           public List<Student> getUsers() {
               return users;
           }

           public void setUsers(List<Student> users) {
               this.users = users;
           }

           @Override
           public void clearData() {
               //TODO 清理数据

           }
       }
```

##### 2、创建处理数据的ViewModel类，必须继承BaseViewModel，负责处理网络或者本地数据
```
    public class StudentViewModel extends BaseViewModel<StudentModel> {
        private StudentModel model = new StudentModel();

        public StudentViewModel(Application application) {
            super(application);
        }

        public void initUser() {
            List<Student> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Student user = new Student("Name" + i, "18", "西安");
                list.add(user);
            }
            model.setUsers(list);
            notifyDataChanged(model);
        }
    }
```

##### 3、Activity或者Fragment，这里仅以Activity为例。
```
    @BindViewModel(StudentViewModel.class)
    public class StudentActivity extends BaseActivity<StudentModel, StudentViewModel> {
        private RecyclerView recyclerView;
        private MyAdapter mAdapter;

        @Override
        public int getLayoutId() {
            return R.layout.act_student;
        }

        @Override
        public void onInitViews(Bundle savedInstanceState) {
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new MyAdapter(new ArrayList<Student>());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

            viewModel.initUser();
        }

        @Override
        public void onModelChanged(StudentModel model) {
            if (model == null) {
                return;
            }
            mAdapter.setNewData(model.getUsers());
        }
    }
```
1. 必须添加@BindViewModel注解来指定处理数据的ViewModel类
2. 指定Model与ViewModel的泛型类型
3. 使用viewModel提供的接口处理数据
4. 在onModelChanged回调方法中监听数据变化，更新UI

### 二、升级版MVP模式用法

##### 1、view创建，只处理UI显示部分,必须继承AppViewDelegate或者IViewDelegate接口，view只提供设置UI数据的方法或者获取控件的方法
```
    public class UserView extends AppViewDelegate {
        private RecyclerView recyclerView;
        private Button btn;
        private MyAdapter mAdapter;

        @Override
        public int getRootLayoutId() {
            return R.layout.act_word;
        }

        @Override
        public void initViews() {
            super.initViews();
            recyclerView = get(R.id.recyclerView);
            btn = get(R.id.btnInsert);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter = new MyAdapter(new ArrayList<User>());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));

        }

        public void setUser(List<User> user) {
            mAdapter.setNewData(user);
        }

        public void onInsertClick(View v) {

        }
     }
```

##### 2、创建DataBinder类，当model数据变化时负责将model的数据更新到对应的view，需要指定view与model的类型
```
    public class UserDataBinder implements IDataBinder<UserView, UserModel> {
        @Override
        public void notifyModelChanged(UserView viewDelegate, UserModel data) {
            //真正通知View 更新UI，根据view层提供的方法设置数据
            viewDelegate.setUser(data.getUsers());
        }
    }
```

###### 3、创建model类，里面包含View需要的所有数据，同简易模式一样，唯一不同的是，这里需要使用@BindDataBinder注解指定dataBinder，可以是多个
```
    @BindDataBinder(dataBinder = UserDataBinder.class)
    public class UserModel implements IModel {
        private List<User> users;

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }

        @Override
        public void clearData() {

        }
    }
```

##### 4、Presenter创建，也就是Activity或者Fragment，必须使用@BindDataBinder注解指定绑定数据的dataBinder
```
    @BindDataBinder(dataBinder = UserDataBinder.class)
    public class UserListActivity extends ActivityPresenter<UserView> {

        private UserModel model;

        @Override
        public Class<UserView> getViewDelegateClass() {
            return UserView.class;
        }

          /**
            * 所有的初始化工作处理完毕回调
            *
            * @param savedInstanceState
            */
        @Override
        public void onPresenterCreated(Bundle savedInstanceState) {
            model = new UserModel();
            UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
            viewModel.getUsers().observe(this, new Observer<List<User>>() {
                @Override
                public void onChanged(List<User> users) {
                    //设置数据
                    model.setUsers(users);
                    //通知更新UI
                    notifyModelChange(model);
                }
            });
        }
    }
```

1. 使用@BindDataBinder注解指定绑定数据的dataBinder
2. 指定View泛型的实际类型
3. getViewDelegateClass（）方法返回对应View代理的class
4. 在onPresenterCreated（）回调方法中处理逻辑，我这里使用了ViewModel来处理的数据

[github：https://github.com/jingzhanwu/JJetpackMvpLib](https://github.com/jingzhanwu/JJetpackMvpLib)

[简书：https://www.jianshu.com/u/897f53e61769](https://www.jianshu.com/u/897f53e61769)

[博客:https://blog.csdn.net/qq_19979101](https://blog.csdn.net/qq_19979101)

