import {createRouter, createWebHashHistory} from 'vue-router'
import AdminNavigator from "@/navigator/AdminNavigator";
import WorkerNavigator from "@/navigator/WorkerNavigator";
import EmployerNavigator from "@/navigator/EmployerNavigator";
import UserMainPage from "@/task/UserMainPage";
import Register from "@/user/Register";
import Login from "@/user/Login";
import PostTaskPage from "@/task/PostTaskPage";
import IssueReportPage from "@/report/IssueReportPage";
import reportDetail from "@/report/ReportDetail";
import EmployerTaskDetail from '@/task/EmployerTaskDetail'
import VisitorNavigator from "@/navigator/VisitorNavigator";
import WorkerTaskDetail from "@/task/WorkerTaskDetail";
import UserInfoPage from "@/user/UserInfoPage";
import AdminMainPage from "@/admin/AdminMainPage";
import WorkerReportPage from "@/report/WorkerReportPage";
import AdminTaskDetail from "@/admin/AdminTaskDetail";
import {checkPermission, getPathType} from "@/utils/util";
import FlawVisualizer from "@/flaw/FlawVisualizer";
import ReportList from "@/report/ReportList";
import FlawModify from "@/flaw/FlawModify";
import WorkerDashBoard from "@/user/WorkerDashBoard";
import WorkerTestContext from "@/user/workerdashboardcontent/WorkerTestContext";
import WorkerActivation from "@/user/workerdashboardcontent/WorkerActivation";
import WorkerAbility from "@/user/workerdashboardcontent/WorkerAbility";
import WorkerDomainKnowledge from "@/user/workerdashboardcontent/WorkerDomainKnowledge";
import TaskWorkerInfo from "@/task/taskstatistics/TaskWorkerInfo"
import BugArrivalCurve from "@/task/taskstatistics/BugArrivalCurve";


const routes = [
    {
        path: '/',
        name: 'VisitorNavigator',
        component: VisitorNavigator,
        children: [
            {
                path: '/register',
                name: 'Register',
                component: Register,
            },
            {
                path:'/',
                name: 'Login',
                component: Login,
            },
            {
                path: '/test',
                name: 'Test',
                component: AdminMainPage
            }
        ]
    },
    {
        path: '/admin',
        name: 'AdminNavigator',
        component: AdminNavigator,
        children: [
            {
                path: '/admin',
                name: 'AdminMainPage',
                component: AdminMainPage
            },
            {
                path: '/admin/taskDetail',
                component: AdminTaskDetail
            }
        ]
    },
    {
        path: '/worker',
        name: 'WorkerNavigator',
        component: WorkerNavigator,
        children: [
            {
                path: '/worker/',
                name: 'WorkerMainPage',
                component: UserMainPage
            },
            {
                path: '/worker/issueReport',
                name: 'IssueReport',
                component: IssueReportPage
            },
            {
                path: '/worker/taskDetail',
                name: 'WorkerTaskDetail',
                component: WorkerTaskDetail,
/*                children: [
                    {
                        path:'/worker/reportList',
                        component: ReportList,
                        name: 'WorkerReportList'
                    }
                ]*/
            },
            {
                path: '/worker/userinfo',
                name: 'WorkerInfo',
                component: UserInfoPage,
                children: [
                    {
                        path: '/worker/userinfo',
                        name: 'WorkerDashBoard',
                        component: WorkerDashBoard,
                        children: [
                            {
                                path: '/worker/userinfo/',
                                name: 'WorkerTestContext',
                                component: WorkerTestContext
                            },
                            {
                                path: '/worker/userinfo/activation',
                                name: 'WorkerActivation',
                                component: WorkerActivation
                            },
                            {
                                path: '/worker/userinfo/ability',
                                name: 'WorkerAbility',
                                component: WorkerAbility
                            },
                            {
                                path: '/worker/userinfo/domainKnowledge',
                                name: 'WorkerDomainKnowledge',
                                component: WorkerDomainKnowledge
                            }
                        ]
                    }
                ]
            },
            {
                path: '/worker/reportDetail',
                name: 'WorkerReportDetail',
                component: reportDetail
            },
            {
                path: '/worker/myReport',
                name: 'WorkerReport',
                component: WorkerReportPage
            },
            {
                path: '/worker/modifyReport',
                name: 'WorkerModifyReport',
                component: FlawModify
            }

        ]
    },
    {
        path: '/employer',
        component: EmployerNavigator,
        name: 'EmployerNavigator',
        children: [
            {
                path: '/employer/',
                name: 'EmployerMainPage',
                component: UserMainPage
            },
            {
                path: '/employer/postTask',
                name: 'EmployerPostTaskPage',
                component: PostTaskPage
            },
            {
                path: '/employer/reportDetail',
                name: 'EmployerReportDetail',
                component: reportDetail
            },
            {
                path: '/employer/taskDetail',
                name: 'EmployerTaskDetail',
                component: EmployerTaskDetail,
                children: [
                    {
                        path: '/employer/taskDetail/flawVisualizer',
                        name: 'FlawVisualizer',
                        component: FlawVisualizer
                    },
                    {
                        path: '/employer/reportList',
                        name: 'ReportList',
                        component: ReportList
                    },
                    {
                        path: '/employer/taskWorkerInfo',
                        name: 'TaskWorkerInfo',
                        component: TaskWorkerInfo
                    },
                    {
                        path: '/employer/bugArrivalCurve',
                        name: 'BugArrivalCurve',
                        component: BugArrivalCurve
                    }
                ]
            },
            {
                path: '/employer/userinfo',
                name: 'EmployerUserInfo',
                component: UserInfoPage
            }
        ]
    }
];


const router = createRouter({
    history: createWebHashHistory(),
    routes: routes

});

router.beforeEach((to, from, next) => {
    let role;
    if(( role = getPathType(to.path) ) == null){
        next();
        return;
    }
    checkPermission(role, (permitted) => {
        if(permitted)next();
        else next({name: 'Login'});
    })
})


export default router;
