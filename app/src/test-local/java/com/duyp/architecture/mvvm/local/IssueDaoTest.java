package com.duyp.architecture.mvvm.local;


import com.duyp.architecture.mvvm.data.local.daos.IssueDao;
import com.duyp.architecture.mvvm.data.local.daos.IssueDaoImpl;
import com.duyp.architecture.mvvm.data.model.Issue;

import org.junit.Test;

import java.util.List;

import javax.inject.Inject;

import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.duyp.architecture.mvvm.test_utils.ModelTestUtils.sampleIssueList;
import static com.duyp.architecture.mvvm.local.RealmTestUtils.initFindAllSorted;
import static com.duyp.architecture.mvvm.local.RealmTestUtils.initRealmQuery;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by duypham on 10/16/17.
 *
 */
public class IssueDaoTest extends BaseRealmTest {

    private static final Long REPO_ID = 100L;

    @Inject
    IssueDaoImpl issueDao;

    private RealmQuery<Issue> mockQuery;
    private RealmResults<Issue> mockResults;

    private final List<Issue> issues = sampleIssueList(20, REPO_ID);

    @Override
    public void setup() throws Exception{
        super.setup();
        testComponent.inject(this);

        mockQuery = initRealmQuery(mockRealm, Issue.class);
        mockResults = initFindAllSorted(mockQuery, issues);

        when(mockQuery.findFirst()).thenReturn(issues.get(0));
    }

    @Test
    public void shouldBeAbleToInjectRealmDatabase() {
        assertThat(mockRealm, is(issueDao.getRealm()));
    }

    @Test
    public void shouldBeAbleToQuery() {
        assertThat(mockRealm.where(Issue.class), is(mockQuery));
        assertThat(mockQuery.findAll(), is(mockResults));
    }

    @Test
    public void getRepoIssuesTest() {

        issueDao.getRepoIssues(1L);

        verify(mockQuery).equalTo(anyString(), anyLong());
        verify(mockQuery).findAllSorted("createdAt", Sort.DESCENDING);

        assertThat(mockResults.size(), is(issues.size()));
    }
}