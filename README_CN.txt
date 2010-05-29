    本框架提供了有关粒子群算法(PSO)和遗传算法(GA)的完整实现，以及一套关于改进、应用、测试、结果输出的完整框架。
    本框架对粒子群算法与遗传算法进行逻辑解耦，对其中的改进点予以封装，进行模块化，使用者可以采取自己对该模块的改进替换默认实现组成新的改进算法与已有算法进行对比试验。试验结果基于Excel文件输出，并可通过设定不同的迭代结束方式选择试验数据的输出方式，包括；
    1. 输出随迭代次数变化的平均达优率数据（设定终止条件区间大于0）。
    2. 输出随迭代次数变化的平均最优值数据（设定终止条件区间等于0）。
    本框架了包含了常用基准函数的实现以及遗传算法与粒子群算法对其的求解方案实现和对比，如TSP，01背包，Banana函数，Griewank函数等。并提供大量工具方法，如KMeans，随机序列生成与无效序列修补方法等等。
    对遗传算法的二进制编码，整数编码，实数编码，整数序列编码（用于求解TSP等），粒子群算法的各种拓扑结构，以及两种算法的参数各种更新方式均有实现，并提供接口供使用者实现新的改进方式并整合入框架进行试验。
    欢迎参考并提出宝贵意见，特别欢迎愿意协同更新修补代码的朋友(邮箱starffly@foxmail.com)。
    代码已作为lakeast项目托管在Google Code:
    http://code.google.com/p/lakeast
    http://code.google.com/p/lakeast/downloads/list

    某些类的功能说明：
    org.lakest.common中：
        BoundaryType定义了一个枚举，表示变量超出约束范围时为恢复到约束范围所采用的处理方式，分别是NONE(不处理)，WRAP（加减若干整数个区间长度），BOUNCE（超出部分向区间内部折叠），STICK（取超出方向的最大限定值）。
        Constraint定义了一个代表变量约束范围的类。
        Functions定义了一系列基准函数的具体实现以供其他类统一调用。
        InitializeException定义了一个代表程序初始化出现错误的异常类。
        Randoms类的各个静态方法用以产生各种类型的随机数以及随机序列的快速产生。
        Range类的实现了用以判断变量是否超出约束范围以及将超出约束范围的变量根据一定原则修补到约束范围的方法。
        ToStringBuffer是一个将数组转换为其字符串表示的类。
    org.lakeast.ga.skeleton中：
        AbstractChromosome定义了染色体的公共方法。
        AbstractDomain是定义问题域有关的计算与参数的抽象类。
        AbstractFactorGenerator定义产生交叉概率和变异概率的共同方法。
        BinaryChromosome是采用二进制编码的染色体的具体实现类。
        ConstantFactorGenerator是一个把交叉概率和变异概率定义为常量的参数产生器。
        ConstraintSet用于在计算过程中保存和获取应用问题的各个维度的约束。
        Domain是遗传算法求解中所有问题域必须实现的接口。
        EncodingType是一个表明染色体编码类型的枚举，包括BINARY(二进制)，REAL(实数)，INTEGER(整型)。
        Factor是交叉概率和变异概率的封装。
        IFactorGenerator参数产生器的公共接口。
        Population定义了染色体种群的行为，包括种群的迭代，轮盘赌选择和交叉以及最优个体的保存。
    org.lakeast.ga.chromosome中：
        BinaryChromosome二进制编码染色体实现。
        IntegerChromosome整数编码染色体实现。
        RealChromosome实数编码染色体实现。
        SequenceIntegerChromosome整数序列染色体实现。
    org.lakeast.pso.skeleton中：
        AbstractDomain提供一个接口，将粒子的位置向量解释到离散空间，同时不干扰粒子的更新方式。
        AbstractFactorGenerator是PSO中参数产生器的公共抽象类。
        AbstractParticle定义了PSO种群中粒子的基本行为，最主要是实现了如何根据现有位置计算得到下一代粒子的位置的合法值。
        ConstraintSet用于在粒子迭代过程中保存和获取应用问题的各个维度的约束。
        AbstractSwarm.java各种拓扑结构的PSO种群的抽象父类，主要实现了种群迭代过程中计算流程的定义以及中间数据被如何输出到测试工具类。
        Domain是PSO算法求解中所有问题域必须实现的接口。
        DynamicFatorGenerator若种群在迭代过程中，w,c1,c2随迭代次数发生变化，那么它们的产生器需要继承这个抽象类。
        Factor封装了w,c1,c2三个参数的字面值。
        Location用于保存和获取迭代中粒子的位置和速度向量的数值。
        NeighborhoodBestParticle定义了采用邻域版本的PSO算法的具体实现。主要是实现了如何根据邻域版本的PSO算法计算下一迭代中的粒子速度。
        RingTopoSwarm定义环拓扑结构的具体实现，主要是定义了如何获取粒子的邻域粒子的方法。
        StaticTopoSwarm静态拓扑结构的PSO算法的抽象父类。
    org.lakeast.pso.swarm中包含粒子群拓扑结构的各种实现，基本见名知意。

    对各种问题的求解样例位于org.lakeast.main包中，以...TaskTest结尾，基本见名知意。
    以ShafferF6DomainTaskTes对ShafferF6函数进行求解（采用的是PSO，遗传算法样例参见TSPValueTaskTest）为例说明求解过程如下：
    1. 入口函数位于org.lakeast.main.ShafferF6DomainTaskTest中，go函数执行。
    2. 在go函数中，首先指定迭代次数(numberOfIterations)，测试多少轮(testCount,多次运行以得到平均达优值)，种群大小(popSize)，邻域大小(neighborhoodSize)，迭代结束条件(exitCondition,由于制定了迭代次数，所以设定为[0,0]，也就是只有达到指定迭代次数才退出)。
    3. 以testCount,numberOfIterations以及迭代结束条件exitCondition为参数构建TestBatch类的实例batch。这个类用来进行管理参与测试的各种具体算法，且把数据结果按指定的格式输出为Excel文件。
    4. 指定PSO中的因子产生方法，采用ExponentFactorGenerator和ConstrictFactorGenerator两种方式（实现位于org.lakeast.pso.gen包）。
    5. Y表示参与测试的算法数目。
    6. Testable是所有可以被TestBatch测试的类需要实现的接口，以提供TestBatch生成结果Excel文件所需要的数据。
    7. Domain接口是所有可以被算法解决的问题所需要实现的接口，比如说明该问题所需要的粒子位置约束范围，速度约束范围，以及适值评估的公司等。这里的Domain被实例化为ShafferF6Domain，也就是按照ShafferF6函数评估适值。
    8. RingTopoSwarm是用来封装环拓扑邻域结构的类，NeighboordBestParticle是配合该类来实现按邻域最优更新速度而不是全局最优来更新。
    9. 各个测试算法都被加入到TestBatch以后，batch.run()开始执行算法比较过程并输出结果Excel文件到C盘根目录（输出路径可在Testable接口中配置，除了生成Excel文件外，还可以通过修改log4j.properties在制定的位置产生运行结果日志）。