package com.duyp.architecture.mvvm.injection.view_model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.duyp.architecture.mvvm.ui.modules.feed.FeedViewModel;
import com.duyp.architecture.mvvm.ui.modules.issue.detail.IssueDetailViewModel;
import com.duyp.architecture.mvvm.ui.modules.login.LoginViewModel;
import com.duyp.architecture.mvvm.ui.modules.main.MainViewModel;
import com.duyp.architecture.mvvm.ui.modules.profile.ProfileViewModel;
import com.duyp.architecture.mvvm.ui.modules.profile.followers.ProfileFollowersViewModel;
import com.duyp.architecture.mvvm.ui.modules.profile.following.ProfileFollowingViewModel;
import com.duyp.architecture.mvvm.ui.modules.profile.overview.OverviewViewModel;
import com.duyp.architecture.mvvm.ui.modules.profile.starred.StarredViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.RepoDetailViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.codeviewer.CodeViewerViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.commit.CommitsViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.contributors.ContributorsViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files.RepoFilesViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files.paths.RepoFilePathsViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.prettifier.ViewerViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.release.ReleasesViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.issues.list.RepoIssuesViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.list.UserReposViewModel;
import com.duyp.architecture.mvvm.ui.modules.search.RepoSearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(GithubViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel mainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel.class)
    abstract ViewModel feedViewModel(FeedViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserReposViewModel.class)
    abstract ViewModel repoListViewModel(UserReposViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel profileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OverviewViewModel.class)
    abstract ViewModel provideOverviewViewModel(OverviewViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileFollowingViewModel.class)
    abstract ViewModel provideProfileFollowViewModel(ProfileFollowingViewModel viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(ProfileFollowersViewModel.class)
    abstract ViewModel provideProfileFollowersViewModel(ProfileFollowersViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(StarredViewModel.class)
    abstract ViewModel provideStarredViewModel(StarredViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RepoDetailViewModel.class)
    abstract ViewModel provideRepoDetailViewModel(RepoDetailViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ViewerViewModel.class)
    abstract ViewModel provideViewerViewModel(ViewerViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RepoIssuesViewModel.class)
    abstract ViewModel provideIssuesViewModel(RepoIssuesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(IssueDetailViewModel.class)
    abstract ViewModel provideIssueDetailViewModel(IssueDetailViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ContributorsViewModel.class)
    abstract ViewModel provideContributorsViewModel(ContributorsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ReleasesViewModel.class)
    abstract ViewModel provideReleasesViewModel(ReleasesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CommitsViewModel.class)
    abstract ViewModel provideCommitsViewModel(CommitsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RepoSearchViewModel.class)
    abstract ViewModel provideRepoSearchViewModel(RepoSearchViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RepoFilePathsViewModel.class)
    abstract ViewModel provideRepoFilePathsViewModel(RepoFilePathsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RepoFilesViewModel.class)
    abstract ViewModel provideRepoFilesViewModel(RepoFilesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CodeViewerViewModel.class)
    abstract ViewModel provideCodeViewerViewModel(CodeViewerViewModel viewModel);
}
