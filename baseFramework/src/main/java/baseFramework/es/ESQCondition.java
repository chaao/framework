package baseFramework.es;

import com.google.common.collect.Lists;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.io.Serializable;
import java.util.List;

public class ESQCondition implements Serializable {

	private static final long serialVersionUID = 1L;

	private QueryBuilder queryBuilder;

	private QueryBuilder postFilter;

	private int size = 100;

	private List<SortBuilder> sortBuilders = Lists.newArrayList();

	public QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	public QueryBuilder getPostFilter() {
		return postFilter;
	}

	public void setPostFilter(QueryBuilder postFilter) {
		this.postFilter = postFilter;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}


	public List<SortBuilder> getSortBuilders() {
		return sortBuilders;
	}

	public void addSort(SortBuilder sortBuilder) {
		this.sortBuilders.add(sortBuilder);
	}
}
